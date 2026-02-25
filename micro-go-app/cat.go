package main

import (
	"io"
	"log"
	"os"
)

func main() {
	// Simple implementation of the `cat` command in Go, which reads from stdin if no arguments are provided,
	// or from files if they are specified as arguments.
	if len(os.Args) == 1 {
		_, err := io.Copy(os.Stdout, os.Stdin)
		if err != nil {
			log.Fatal(err)
		}
	} else {
		for _, fname := range os.Args[1:] {
			fh, err := os.Open(fname)
			if err != nil {
				log.Fatal(err)
			}

			_, err = io.Copy(os.Stdout, fh)
			if err != nil {
				log.Fatal(err)
			}
		}
	}
}
