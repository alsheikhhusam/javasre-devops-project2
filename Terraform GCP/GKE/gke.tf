# Variables
variable "gke_username" {
  default     = ""
  description = "gke username"
}

variable "gke_password" {
  default     = ""
  description = "gke password"
}

variable "gke_num_nodes" {
  default     = 1 # 1 Nodes for each zone
  description = "number of nodes to deploy into the cluster"
}

# GKE cluster
resource "google_container_cluster" "primary" {
  name     = "project2-gke"
  location = var.region

  # we need to create at least a single node cluster
  # and then delete it in order to create a seperately
  # managed node pool
  remove_default_node_pool = true
  initial_node_count       = 1

  network    = google_compute_network.project2-vpc.name
  subnetwork = google_compute_subnetwork.project2-subnet.name
}

# separately managed node pool
resource "google_container_node_pool" "primary_nodes" {
  name       = "${google_container_cluster.primary.name}-node-pool"
  location   = var.region
  cluster    = google_container_cluster.primary.name
  node_count = var.gke_num_nodes

  node_locations = [ # Only 2 zone. limits number of nodes created
    "us-central1-a",
    "us-central1-b",
  ]

  node_config {
    oauth_scopes = [
      "https://www.googleapis.com/auth/logging.write",
      "https://www.googleapis.com/auth/monitoring"
    ]

    labels = {
      env = var.project_id
    }

    machine_type = "n2-standard-2"
    tags         = ["gke-node", "${var.project_id}-gke"]
    metadata = {
      disable-legacy-endpoints = "true"
    }
  }
}