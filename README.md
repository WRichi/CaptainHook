# CaptainHook
An app that searches songs or playlists on spotify and download them via youtube.

Features
MVVM
Room database to store downloaded songs and playlists (thumbnail link, Name (Interpret/ersteller der playlist), Album)
Search track or playlist in spotify
Show them in an RecyclerView
Click on an item and a new screen will be opened to search this track on youtube (or the first song of the playlist and subsequent ones)
5-10 results in an recyclerview of the youtube search results
If a youtube search item is clicked, add it to the history, download it and store it in the database
If you chose an playlist before we go through each track in the playlist from spotify and search it in youtube to find the best video to download
Download thumbnails with Glider/Picasso (displaying spotify and youtube thumbnails)
Retrofit for network communication (JobScheduler - spotify, youtube and jdownloader requests)
