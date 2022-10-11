kill -9 $(pidof java)
mv logs/genshindataviewer.log logs/genshindata.log.$(date "+%Y%m%d%H%M%S")
