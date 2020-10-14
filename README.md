# Reddit.Web
Use this tool live at [this link](https://basselr.github.io/Reddit.Web/)!  

A web-graphing tool for visualizing the connections between subreddits. Connections are based on subreddit posts that hyperlink to another subreddit.  

Dataset about subreddits obtained from (.csv file): http://snap.stanford.edu/data/soc-RedditHyperlinks.html  
  
Backend built with Java & Maven, featuring custom-built versions of Dijkstra's algorithm (graph theory) and quicksort (sorting algorithms). Formatted data is outputted as JSON.  
  
Frontend loads, processes and renders the JSON content as Canvas elements. Built with HTML5, CSS3, ES6 JS and Canvas.  
  
![Preview](/preview.PNG)  

---  

Setting up:  

This data is stored in a .csv file named "DataBody.csv", in the src/main/resources folder.  

IMPORTANT ASSUMPTION: The Graph.initialize("DataBody.csv") must be called before any other function, to initialize the graph.  

When opening with Eclipse, the project should be good to go, assuming Maven is installed. However, you may encounter the following:  

1)  An error complaining about source not being supported for JRE version below 1.7. It should give you a quick fix to set the JRE to use version 1.7.  

2)  The project may require building through Maven. Do so by right clicking on the project > Run As > Maven Build. If it prompts, you can write
    'clean verify' in the goal.  

3)  To run, right click on the project and go to Run As > Java Application. Click 'TestDriver - com.group1.app' and hit OK. It should now run.  

4)  Note: Refresh the Eclipse directory after the Java application runs, so that the JSON will show up. This JSON can now be used in the front end.  

  

Included Functionality:  

1)  Ensure the Graph is initialized, as stated above.  

2)  IMPORTANT ASSUMPTION: Dijkstra.generateSPT(String src) must be called before any other access routine within the class (with a valid SubReddit name as
    a source), to initialize the shortest path tree. Dijkstra's algorithm can be used to find the shortest path (minimized negative sentiment) from the specified src to another SubReddit in the SPT. Path is returned in the form of a printable ArrayList. It can also be used to find the cost to visit a particular SubReddit from the previously used source SubReddit. See Dijkstra.java documentation.  

3)  To check for the existence of a path from one SubReddit to another, the Graph class can perform a Breadth First Search or a Depth First Search, using 
    their respective methods. The searches take a source SubReddit name and a destination SubReddit name and return the path in the form of a printable Dequeue of Strings representing SubReddit names.  

4)  The Graph class can generate an ArrayList of all unique SubReddit objects found in the data set. This list can be sorted in descending order by the
    number of outgoing connections. This sorting is done using the quicksort algorithm, implemented in QuickSort.java.  

5)  The Graph class holds references to all actual SubReddit objects, and these can be accessed using Graph.getSubRedditPointer(String name).  

6)  These SubReddit objects have lists of Edge objects representing the outgoing edges from a given SubReddit. A reference to this list of Edge objects
    can be accessed with the getEdgeList() method. Each of these Edge objects contain various properties accessed using the getProperty() method, using a Prop enumeration. See SubReddit.java, Edge.java, and Prop.java documentation.  

7)  A JSON file can be initialized with a specified number of "parent" SubReddits (x) with a specified maximum of outgoing edges (y). These SubReddits will 
    be randomly selected from the data set, with the given constraints considered. This is done with JSONWriter.writeJSON(x, y). It is recommended, for visual purposes, to keep x = 10, and y = 4. This JSON file will be named "nodeData.json" and will be placed in the root directory of the project. It can be loaded into the front end website (specified above) for visualization! Running the writeJSON routine again will generate a new JSON file with new data, and the front end can be refreshed to load a new JSON file.  

8)  All MAVEN documentation is located in the site folder. Information on dependencies can be accessed through the site located in site/project-info.html.  
