The program works by first creating a trie of all the words in the file. The trie is used to find all the suffixes of a word. A word is considered a compound word if it can be constructed by concatenating two or more suffixes of other words.

The program works by iterating over the trie and checking if each word can be constructed by concatenating two or more suffixes of other words. If it can, then the program adds the word to a queue. The program then removes the first word from the queue and checks if it can be constructed by concatenating two or more suffixes of other words. If it can, then the program adds the word to the queue and repeats the process.

The program terminates when the queue is empty. The longest and second-longest compound words are the longest and second-longest words in the queue, respectively. The total amount of compound words is the number of words in the queue.

Time complexity
The time complexity of the program is O(n log n), where n is the number of words in the file. This is because the program needs to sort the words in the trie by length. Once the words are sorted, the program can find the longest compound word in the file in linear time.

Example usage
To use the program, simply compile it and run it with the path to the file as the argument. For example, to find the longest compound word in the file Input_01.txt
