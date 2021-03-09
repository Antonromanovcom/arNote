#!/bin/bash

rm -f ~/IdeaProjects/arNote/src/main/resources/static/*.*
cp -r ~/IdeaProjects/arNoteUI/dist/arnote/* ~/IdeaProjects/arNote/src/main/resources/static

echo " ========= Done! ============== "
