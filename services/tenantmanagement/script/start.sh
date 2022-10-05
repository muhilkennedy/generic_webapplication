echo "####################################################"
echo "####################################################"
echo  Compiling Modules
echo "####################################################"
echo "####################################################"
cd ../
mvn clean install -Dskip.tests=false

echo "####################################################"
echo "####################################################"
echo  Starting Server
echo "####################################################"
echo "####################################################"

cd core
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5000" -Dspring.cloud.vault.token=00000000-0000-0000-0000-000000000000