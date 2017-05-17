
A Google Image Search app which allows a user to select search filters and paginate results infinitely

User can enter a search query that will display a grid of image results from the Google Image API.<br>
User can click on "settings" which allows selection of advanced search options to filter results<br>
 User can configure advanced search filters such as:<br>
&nbsp;&nbsp;&nbsp;&nbsp;Size (small, medium, large, extra-large)<br>
&nbsp;&nbsp;&nbsp;&nbsp;Color filter (black, blue, brown, gray, green, etc...)<br>
&nbsp;&nbsp;&nbsp;&nbsp;Type (faces, photo, clip art, line art)<br>
&nbsp;&nbsp;&nbsp;&nbsp;Site (espn.com)<br>
Subsequent searches will have any filters applied to the search results<br>
User can tap on any image in results to see the image full-screen<br>
User can scroll down “infinitely” to continue loading more image results (up to 8 pages)<br>
Robust error handling, check if internet is available, handle error cases, network failures<br>
Use the ActionBar SearchView or custom layout as the query box instead of an EditText<br>
User can share an image to their friends or email it to themselves<br>
Replace Filter Settings Activity with a lightweight modal overlay<br>

<img src="https://raw.githubusercontent.com/cassiomo/MyImageSearcher/master/imagesearch.gif">
