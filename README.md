# LazyListview

1. MainActivity calls JsonParse function to parse JSON from URL provided.
2. This is implemented in Async Task.
3. OnPostExecute, create Listview and populate title, description and image details.
4. LazyImageLoadAdapter and ImageLoader helps to load the image lazily in Listview.
5. FileCache, MemoryCache are handled.
6. WebRequest class helps to get JSON from http URL.
7. Refresh button helps to update from cache.
8. This is tested with Moto G2 - Lollypop Android version
