job "email-sender-service" {
  datacenters = ["dc1"]
  type = "service"
  group "emails" {
    count = 2
    task "emails-api" {
      driver = "docker"
      config {
        image = "punchat/email-sender"
        network_mode = "punchat"
      }
      env {
        PORT = "${NOMAD_HOST_PORT_http}"
        clientId = "emails"
        clientSecret = "pass"
      }
      resources {
        cpu = 300
        memory = 300
        network {
          port "http" {}
        }
      }
    }
    restart {
      attempts = 1
    }
  }
}