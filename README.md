# CaptainHook
An app that searches songs or playlists on spotify and downloads them via youtube.
The app is called CaptainHook because it supports advanced piracy (Arr!).

Features:<br>
* searching tracks/playlist on spotify
* youtube browsing queue with the selected tracks to select files to download
* downloading songs
* displaying downloaded songs in history

Project Requirements:
* MVVM &#9745;
* ROOM database &#9745; (storing downloaded songs in history)
* RecyclerView &#9745; (in every activity)
* Glider &#9745; (downloading thumbnails - we also used Picasso)
* Retrofit &#9745; (used for Spotify Web API requests)
* JobScheduler &#9745; (user can schedule the download of songs when wifi is available - user only gets prompted if using a mobile connection)
* Tests &#9745;

APIs:
* Spotify Web API
* Youtube API v3
