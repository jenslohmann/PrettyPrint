# PrettyPrint

Pretty prints single line xml from stdin to stdout.

Is especially useful in case of logfiles with lines and lines of (metadata and) xml.
Any metadata before the first `<` will be printed as an xml comment.

Example usage:

`$ cat bunchofxml.log|pretty`

License: MIT

PS: Remember to have run `${GRAALVM_HOME}/bin/gu install native-image`