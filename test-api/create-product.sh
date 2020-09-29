echo "Executando o curl...";

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{ "name": "parafuso", "description" : "parafusos", "qnt" : 10, "obs" : "contem 10 unidades", "uuidCustomer": "56eb9271-1dbd-49a6-86c0-55a4ceb1c70c"}' \
  http://localhost:8082/v1/customers/629fa538-6651-49a6-9025-862d6f70f1c8/products

echo "\n";