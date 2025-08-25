package lutfiangg20.demo_teknis;

import org.springframework.boot.SpringApplication;

public class TestDemoTeknisApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoTeknisApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
