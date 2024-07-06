package tech.group15.thriftharbour;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.repository.UserRepository;

@SpringBootApplication
public class ThriftHarbourApplication implements CommandLineRunner {

  private final UserRepository userRepository;

  public ThriftHarbourApplication(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(ThriftHarbourApplication.class, args);
  }

  @Override
  public void run(String... args) {

    /* Admin creation on application startup */
    User adminAccount = userRepository.findByRole(RoleEnum.ADMIN);
    if (adminAccount == null) {
      User admin = new User();

      /* As per client requirement, adding the Admin on boot. */
      admin.setFirstName("admin");
      admin.setLastName("admin");
      admin.setEmail("admin@dal.ca");
      admin.setPassword(new BCryptPasswordEncoder().encode("Admin@123"));
      admin.setRole(RoleEnum.ADMIN);

      userRepository.save(admin);
    }
  }
}
