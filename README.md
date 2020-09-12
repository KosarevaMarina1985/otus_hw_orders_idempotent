- namespace: kafka

  - kubectl create namespace kafka
  - kubectl config set-context --current --namespace=kafka
  
- enable ingress:
  - minikube addons enable ingress
 
    
- Spring Cloud Kubernetes requires access to Kubernetes API in order to be able to retrieve 
a list of addresses of pods running for a single service. 
   - kubectl create clusterrolebinding admin --clusterrole=cluster-admin --serviceaccount=kafka:default

- kafka:
  - helm repo add confluentinc https://confluentinc.github.io/cp-helm-charts/
  - helm repo update
  - helm install cp confluentinc/cp-helm-charts -f cp_values.yaml

- manifests:
  - kubectl apply -f secret.yaml -f app-config.yaml -f persistentVolumeClaim.yaml -f mysql.yaml
  - kubectl apply -f user-deployment.yaml -f billing-deployment.yaml -f order-deployment.yaml -f notification-deployment.yaml  
  - kubectl apply -f ingress.yaml
  

