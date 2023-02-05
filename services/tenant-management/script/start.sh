echo "####################################################"
echo "####################################################"
echo  Compiling Modules
echo "####################################################"
echo "####################################################"
cd ../
mvn clean install -Dskip.tests=true

echo "####################################################"
echo "####################################################"
echo  Starting Server
echo "####################################################"
echo "####################################################"

cd core/target
java -jar core-v1.0.0.jar -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5000" -Ddefault.dir=/var/tm/ -Dspring.cloud.vault.token=00000000-0000-0000-0000-000000000000