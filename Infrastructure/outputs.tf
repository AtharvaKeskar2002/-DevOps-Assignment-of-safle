output "instance_ip" {
  value = google_compute_instance.app_server.network_interface[0].access_config[0].nat_ip
}

output "database_instance_name" {
  value = google_sql_database_instance.db_instance.name
}
