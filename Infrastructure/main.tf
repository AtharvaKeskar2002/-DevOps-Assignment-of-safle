resource "google_compute_instance" "app_server" {
  name         = "app-server"
  machine_type = "e2-medium"
  zone         = "YOUR_GCP_ZONE"

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-10"
    }
  }

  network_interface {
    network = "default"

    access_config {
    }
  }

  metadata_startup_script = file("startup.sh")
}

resource "google_sql_database_instance" "db_instance" {
  name             = "db-instance"
  database_version = "MYSQL_5_7"
  region           = "YOUR_GCP_REGION"

  settings {
    tier = "db-f1-micro"
  }
}

resource "google_sql_database" "database" {
  name     = "votingapp"
  instance = google_sql_database_instance.db_instance.name
}
