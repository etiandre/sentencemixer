import xml.etree.ElementTree as ET
import os
import sys
import subprocess

if len(sys.argv) < 3:
    print("Usage : {} <url> <voice name>".format(sys.argv[0]))
    exit(1)

url = sys.argv[1]
name = sys.argv[2]
offset = 0.0

if (os.path.isdir(name)):
    print("Oh noes directory exists")
    exit(1)

os.mkdir(name)

subprocess.run(["youtube-dl", "--write-auto-sub", "--sub-lang", "fr", "-x","--audio-format", "wav",  "--sub-format","srv3", "-o", name+"/temp.wav", url])

f = open(name+"/wordMarkers.txt", "w")

tree = ET.parse(name+"/temp.fr.srv3")
root = tree.getroot()
markers = []
for p in root.find("body").findall("p"):
    for s in p:
        parentTime = int(p.get("t"))
        childTime = 0
        if (s.get("t") != None):
            childTime = int(s.get("t"))
        startTime = (parentTime+childTime)/1000 + offset
        words = s.text.strip()
        markers.append((startTime, words))

for i in range(len(markers)):
    if i != len(markers) - 1:
        f.write("{}\t{}\t{}\n".format(
            markers[i][0],
            markers[i+1][0],
            markers[i][1]
        ))
    else:
        lastp = root.find("body").findall("p")[-1]
        endTime = (int(lastp.get("t")) + int(lastp.get("d")))/1000
        f.write("{}\t{}\t{}\n".format(
            markers[i][0],
            endTime,
            markers[i][1]
        ))

#os.remove(name+"/temp.fr.srv3")
subprocess.run(["ffmpeg", "-i", name+"/temp.wav", name+"/audio.wav"])
os.remove(name+"/temp.wav")