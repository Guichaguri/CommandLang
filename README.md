# CommandLang
A programming language created to work with Minecraft command blocks and functions.

This is an experiment, a simple language parser, interpreter and compiler.

## Why?
Command blocks and functions aren't easily scalable, that can be a problem in huge projects.

This language aims to change that, making it easier to write a single code-base that can be compiled into different versions.

## Structure
Following the directories in the project:

* **Core**: The main component of the language
  * **Lexer**: Parses the code into tokens
  * **Interpreter**: Transforms the tokens into instructions
  * **Compiler**: Compiles the instructions into command block structures or functions
  * **Runtime**: Interprets and runs the language in-game.
* **Mod**: A runtime for Minecraft Forge
* **Plugin**: A runtime for Bukkit and Sponge
* **Tools**: The standalone tool for compiling the language