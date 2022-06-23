package COMP250_A4_W2020;
import java.util.ArrayList;

public class Twitter {

	//ADD YOUR CODE BELOW HERE
	private ArrayList<Tweet> tweets;
	private ArrayList<String> stopWords;

	MyHashTable<String, Tweet> authorAndLatest;
	MyHashTable<String, ArrayList<Tweet>> authorAndTweets;
	MyHashTable<String, ArrayList<Tweet>> dateAndTweet;

	MyHashTable<String, Integer> wordOccurence;
	MyHashTable<String,Integer> stopWordsTable;
	//ADD CODE ABOVE HERE 


	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		//ADD YOUR CODE BELOW HERE
		this.tweets = tweets;
		this.stopWords = stopWords;


		this.dateAndTweet = new MyHashTable<String, ArrayList<Tweet>>(tweets.size());
		this.authorAndLatest = new MyHashTable<String, Tweet>(tweets.size());
		this.authorAndTweets = new MyHashTable<String, ArrayList<Tweet>>(tweets.size());

		wordOccurence = new MyHashTable<String, Integer>(tweets.size()*2); //initial size doesn't rlly matter

		//Complexity: finite number of stuff in stopWords
		//System.out.println("stopWords size is " + stopWords.size());
		stopWordsTable = new MyHashTable<String,Integer>(stopWords.size()*2);
		for (String s : stopWords){
			//System.out.println(s); //error is not population, must be in finding them
			if (stopWordsTable.get(s) == null){ //would handle any duplicates
				stopWordsTable.put(s.toLowerCase(), 1);//O(1) //WOULD HAVING A UNIQUE VALUE MATTER?
			}
		}
		//System.out.println("stopWordsTable size is " + stopWordsTable.size());


		//populate dateAndTweet (date and all tweets of the day) and authorAndTweets (author and all tweets made by them)
		//O(n)
		for (Tweet t : tweets) {
			//O(1)
			String date = t.getDateAndTime();
			String dateOnly = date.substring(0,10);


			//do this for dateAndTweet
			//O(1)
			ArrayList<Tweet> dateList = dateAndTweet.get(dateOnly);
			if (dateList == null){
				ArrayList<Tweet> firstFound = new ArrayList<Tweet>();
				firstFound.add(t);
				//O(1)
				dateAndTweet.put(dateOnly, firstFound);
			}
			else{
				dateList.add(t);
				//O(1)
				dateAndTweet.put(dateOnly, dateList);//populate date-tweet table
			}


			//do this for author and tweets
			String author = t.getAuthor();
			//O(1)
			ArrayList<Tweet> authorList = authorAndTweets.get(dateOnly);
			if (authorList == null){
				ArrayList<Tweet> firstFound = new ArrayList<Tweet>();
				firstFound.add(t);
				//O(1)
				authorAndTweets.put(author, firstFound);
			}
			else{
				authorList.add(t);
				//O(1)
				authorAndTweets.put(dateOnly, authorList);//populate date-tweet table
			}

			//FOR SCREENSHOT PURPOSES: This is in a loop -> for (Tweet t : twitter)
			//for trendingTOPICS
			ArrayList<String> parsedWords = getWords(t.getMessage().toLowerCase());//get all words in that tweet
			MyHashTable<String, Integer> alreadyThere = new MyHashTable<String,Integer>(parsedWords.size() *2); //words already accessed
			for(String word: parsedWords){//for each word in that tweet
				if (stopWordsTable.get(word) == null){//if you can't find it in the stopWord table
					//add it
					if (wordOccurence.get(word) != null){ //if the word is already in the wordOccurence Table,
						if (alreadyThere.get(word) == null){ //change this to a hashTable to incr efficiency
							alreadyThere.put(word, 1); //catalog words reached already
							Integer i = wordOccurence.get(word); //gets the occurence value
							wordOccurence.put(word, ++i);
						}
					}
					else { //if word is not in the wordOccurence Table yet,
						wordOccurence.put(word, 1); //will have one occurence
					}
				}
			}

		}
		//O(m)
		//get the latest tweet by author
		for (Tweet t : tweets) {
			//O(1)
			String tAuthor = t.getAuthor(); //author of the current tweet
			ArrayList <Tweet> tweetsByAuthor = new ArrayList<Tweet>();
			//O(1)
			if (authorAndTweets.get(tAuthor) != null){
				//O(1)
				tweetsByAuthor = authorAndTweets.get(tAuthor); //array list of tweets by an author
			}

			//O(1)
			if (tweetsByAuthor.size() > 0){ //if they have had previous tweets, then iterate through them
				//O(1)
				Tweet temp = tweetsByAuthor.get(0);

				//O(num tweets an author has)
				for (Tweet i : tweetsByAuthor) { //for each tweet the author has written, find the most recent
					if (i.getDateAndTime().compareTo(temp.getDateAndTime()) >= 0) { //if the date is more recent
						temp = i; //that becomes the Tweet you want to correspond to an author
					}
				}
				//O(1)
				//once done finding the latest tweet, then add it to the hashTable
				authorAndLatest.put(tAuthor, t);
			}
			else { //if no previous tweets then set the latest tweet to this one and put in hashTable
				//O(1)
				authorAndLatest.put(tAuthor, t);
			}
		}
		//ADD CODE ABOVE HERE 
	}
	
	
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {
		if (tweets.size() == 0){
			ArrayList<Tweet> newTweets = new ArrayList<Tweet>();
			newTweets.add(t);
		}
		tweets.add(t);
	}
	

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
		//O(1)
    	if (this.authorAndLatest.get(author) != null) return this.authorAndLatest.get(author);
    	return null;
    }


    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String d) { //works!!!
        //ADD CODE BELOW HERE
		//O(1)
		//ArrayList<Tweet> myTweetsFromDate = dateAndTweet.get(d);
		if (this.dateAndTweet.get(d) != null) return this.dateAndTweet.get(d);
		return null;
    	
        //ADD CODE ABOVE HERE
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
        //ADD CODE BELOW HERE
		//O(nlogn)
		ArrayList<String> result = MyHashTable.fastSort(wordOccurence);
		return result;
		//MyHashTable<String, Integer> wordOccurence = new MyHashTable<String, Integer>(tweets.size()*2); //initial size doesn't rlly matter
		/*
		//Complexity: finite number of stuff in stopWords
		MyHashTable<String,Integer> stopWordsTable = new MyHashTable<String,Integer>(stopWords.size()*2);
		for (String s : stopWords){
			stopWordsTable.put(s, 1);//O(1)
		}
		*/



		/*
		//O(m)
		for (Tweet t : tweets){//for each tweet in tweets
			ArrayList<String> parsedWords = getWords(t.getMessage().toLowerCase());//get all words in that tweet
			MyHashTable<String, Integer> alreadyThere = new MyHashTable<String,Integer>(parsedWords.size() *2); //words already accessed
			for(String word: parsedWords){//for each word in that tweet
				if (stopWordsTable.get(word) == null){//if you can't find it in the stopWord table
					//add it
					if (wordOccurence.get(word) != null){ //if the word is already in the wordOccurence Table,
						if (alreadyThere.get(word) == null){ //change this to a hashTable to incr efficiency
							alreadyThere.put(word, 1); //catalog words reached already
							Integer i = wordOccurence.get(word); //gets the occurence value
							wordOccurence.put(word, ++i);
						}
					}
					else { //if word is not in the wordOccurence Table yet,
						wordOccurence.put(word, 1); //will have one occurence
					}
				}
			}
		}
		*/


		//System.out.println("wordOccurence size is " + wordOccurence.size());




		//THE IDEA
		//for each tweet in tweets
			//get all words in that tweet
			//for each word in that tweet
				//if you can't find it in the stopWord table
					//if you can't find it in the wordOccurence table
						//add it to the hashTable //increase counter

		//ArrayList <K> result = HashTable with word, occurence . fastSort

        //ADD CODE ABOVE HERE    	
    }
    
    
    
    /**
     * An helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;
    			
    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }

    

}
