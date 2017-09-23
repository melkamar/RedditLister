# Redditlister

A simple app used for practice and as a future-reference cookbook.
It displays Reddit posts an. Not much more to it.

## Topics covered

#### RecyclerView
The main RecyclerView handles multiple types of items. The logic can be found here:
  - [RecyclerView Adapter](https://github.com/melkamar/android-example-redditlister/blob/master/app/src/main/java/cz/melkamar/redditlister/adapters/PostAdapter.java)
  - [Associated ViewHolders](https://github.com/melkamar/android-example-redditlister/tree/master/app/src/main/java/cz/melkamar/redditlister/viewholders)


#### AsyncTask
AsyncTask is used to fetch data and load them into the Adapter/RecyclerView.
 - [Sources](https://github.com/melkamar/android-example-redditlister/blob/master/app/src/main/java/cz/melkamar/redditlister/util/RefreshATask.java)


#### SwipeRefreshLayout
Layout used to provide swipe-down-to-refresh-RecyclerView.

  - [MainActivity](https://github.com/melkamar/android-example-redditlister/blob/master/app/src/main/java/cz/melkamar/redditlister/activities/MainActivity.java) - in `onCreate()` and `onRefreshFinished()`

#### SettingsActivity, SettingsFragment
Used to easily let user set settings, automatically reads/writes to `SharedPreferences`.

  - [SettingsActivity](https://github.com/melkamar/android-example-redditlister/blob/master/app/src/main/java/cz/melkamar/redditlister/activities/SettingsActivity.java)
  - [SettingsFragment](https://github.com/melkamar/android-example-redditlister/blob/master/app/src/main/java/cz/melkamar/redditlister/activities/SettingsFragment.java)
  - [XML definition of settings](https://github.com/melkamar/android-example-redditlister/blob/master/app/src/main/res/xml/pref_visualizer.xml)
  - [Reading preferences](https://github.com/melkamar/android-example-redditlister/blob/master/app/src/main/java/cz/melkamar/redditlister/activities/MainActivity.java) - `SharedPreferences`.


#### Toolbar menu
  - Checklist :
    - Implement `onCreateOptionsMenu()`
    - Implement `onOptionsItemSelected()`
  - [Menu XML definition](https://github.com/melkamar/android-example-redditlister/blob/master/app/src/main/res/menu/menu_main.xml)
  - [Android Manifest](https://github.com/melkamar/android-example-redditlister/blob/master/app/src/main/AndroidManifest.xml) - implement `parentActivityName` to enable menu-back-arrow navigation.