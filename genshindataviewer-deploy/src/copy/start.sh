if [ ! -d "logs" ]; then
  mkdir logs
fi
nohup java -jar -Xmx3G -Xms3G genshindataviewer.jar >logs/genshindataviewer.log 2>&1 &