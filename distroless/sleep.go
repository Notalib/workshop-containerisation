package main

import (
	"fmt"
	"time"
)

func main() {
	seconds := 0

	for {
		fmt.Printf("Container alive for %d seconds\n", seconds)
		time.Sleep(1 * time.Second)
		seconds++
	}
}
