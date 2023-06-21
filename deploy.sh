## 가동중인 awsstudy 도커 중단 및 삭제
#docker ps -a -q --filter "name=pocket-art" | grep -q . && docker stop pocket-art && docker rm pocket-art | true
#
## 기존 이미지 삭제
#docker rmi bonsik/pocket-art-project-images:1.0
#
## 도커허브 이미지 pull
#docker pull bonsik/pocket-art-project-images:1.0
#
## 도커 run
#docker run -d -p 8888:8888 --name pocket-art bonsik/pocket-art-project-images:1.0
#
## 사용하지 않는 불필요한 이미지 삭제 -> 현재 컨테이너가 물고 있는 이미지는 삭제되지 않습니다.
#docker rmi -f $(docker images -f "dangling=true" -q) || true