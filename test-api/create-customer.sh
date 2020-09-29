echo "Executando o curl...";

curl --header "Content-Type: application/json" \
  --request POST \
  --data '{ "name": "Joao", "lastName": "Silva",  "phone": "3333-88833", "birthDate": "12/10/1998"}' \
  http://localhost:8080/v1/

echo "\n";