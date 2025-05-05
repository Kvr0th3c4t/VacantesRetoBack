package vacantes.modelo.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of="email")
@Builder
@Entity
@JsonIgnoreProperties({"authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "password", "enabled"})
@Table(name="usuarios")
public class Usuario implements UserDetails, Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String email;
	private String nombre;
	private String apellidos;
	private String password;
	private int enabled; //o boolean
	@Column(name="fecha_Registro")
	private Date fechaRegistro;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return Stream.of(this.rol)
	        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
	        .collect(Collectors.toList());
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return this.email;
	}
	
	@JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

	@JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

	@JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
	@JsonIgnore
    @Override
    public boolean isEnabled() {
        if(this.enabled == 1) {
        	return true; 
        }else {
        	return false; 
        }
    }
}
