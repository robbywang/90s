# exort will pass Parent variable to child process, like P2
# source or . will pass varaible to Parent process, like C1
P1=robby
export P2=coco
./child.sh
echo 'after "./child.sh"' C1: $C1

source ./child.sh
echo 'after "source ./child.sh"' C1: $C1
