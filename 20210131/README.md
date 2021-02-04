#Code Sample for Leapyear

[Details](https://leapyear.github.io/soccer-ranking-project/)

## Setup

Make sure you have a relatively fresh gradle, e.g. v.6.8.1 (to install it, you can run `sdk install gradle 6.8.1`; if you don't have `sdkman`, check out [its page](https://sdkman.io/usage)).

You will need Scala ver. 13.4 or fresher.

To (compile and) run the code, use `run` script. 

## Running

as specified, we can run it the following ways:
`./run INPUTFILENAME` or
`cat INPUTFILENAME | ./run` or
`./run <INPUTFILENAME` - whichever is convenient for you.

The only problem is that if the input file is missing, `.run` does not show a nice error message; for that one should probably build a jar and run the code using regular jvm cli.

## Tests
There's a bunch of test cases in AppSuite.

There are some issues with its behavior if the code failes, e.g. due to missing input file.

## Notes
I enjoyed the task; did have to spend a lot of time finding the right gradle etc. Good problem, though. Thank you!