# Spring Boot Application with Prometheus and Grafana Integration

This repository contains a sample Spring Boot application configured to collect metrics using Prometheus and visualize them using Grafana. 

## Setup

### Prerequisites

Make sure you have the following installed on your system:

- Java Development Kit (JDK) version 8 or later
- Maven build tool
- Docker (for running Prometheus and Grafana containers)

### Running the Application

1. Clone this repository to your local machine:

    ```bash
    git clone https://github.com/tusharlimbhore/spring-boot-grafana.git
    ```

2. Navigate to the project directory:

    ```bash
    cd prometheus-demo
    ```

3. Build the Spring Boot application using Maven:

    ```bash
    mvn clean package
    ```

4. Run the Spring Boot application:

    ```bash
    java -jar target/prometheus-demo.jar
    ```

### Prometheus and Grafana Setup

1. Start Prometheus and Grafana using Docker Compose:

    ```bash
    docker-compose up -d
    ```

2. Access Prometheus at [http://localhost:9090](http://localhost:9090) and Grafana at [http://localhost:3000](http://localhost:3000). Default credentials for Grafana are admin/admin.

3. Configure Prometheus to scrape metrics from the Spring Boot application by adding the following to your `prometheus.yml` file:

    ```yaml
    scrape_configs:
      - job_name: 'spring-boot-app'
        metrics_path: '/actuator/prometheus'
        static_configs:
          - targets: ['spring-boot-app:8080']
    ```

4. Import the Prometheus and Grafana dashboards available in the `dashboards` directory.

## Usage

- Once the Spring Boot application is running and Prometheus and Grafana are set up, you can access various metrics exposed by the application through Prometheus and visualize them using Grafana dashboards.
- Explore and customize Grafana dashboards as per your requirements.

## Contributing

Contributions are welcome! Please fork this repository, make your changes, and submit a pull request.