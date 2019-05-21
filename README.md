# re.etiand.SentenceMixer
re.etiand.SentenceMixer is a simple TTS engine which was made so voices are easy to create. It uses the same techniques as sentence-mixing in Youtube Poops. Work in progress.
A sample french voice ("boule", from famous french ytp source la boule magique) is provided.

It expects espeak (for word-to-phoneme translation) and sox (for audio output) to be installed.
## Usage
First build with `mvn package`.

Then launch `java -jar target/sentencemixer-*.jar <voice>`. The program reads sentences to be spoken from its standard input.

## Creating voices
The exact voice format is subject to change.

A voice consists a directory containing two files : an audio file (`<voice>.wav`), and a markers file (`<voice>.txt`).
The markers file is created audacity : First, import the desired audio file in audacity, then annotate every spoken word needed using a marker track.
Then export the marker track to `voice.txt`.

You can use `boule.aup` as an example.

## Limits
The search algorithm (`Voice::findBestCandidates`) is not correct, because the correct one is supposedly too slow. The one provided is good enough®

