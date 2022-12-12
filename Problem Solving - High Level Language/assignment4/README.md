# Web Scraping

Assignment 4: Wikipedia scraping by using python, regular expressions and beautifulsoup.

## Versions of Dependencies
The requirements for this assignment is in the `requirements.txt`. Run the following for installing:
```
pip3 install -r requirements.txt
```
or manually:
```
py -m pip install [...]
```

## How to run the code? (Usage)
You can run the files: wiki_race_challenge.py & fetch_player_statistics.py, like this:

```
py [name_of_file].py
```
example:
```
py wiki_race_challenge.py
```

## Under progress (WIP)
Task 5 & 6 (extract_events() & render_schedule()) in time_planner.py is still under progress.

Bonus: Collect_dates.py (Task 4) is still under progress.

## Testing
You can test the package by using pytest:
```
py -m pytest tests/test_[...].py
```
or
```
pytest tests/test_[...].py
```

Tests that pass by using pytest:
test_files.py,
test_requesting.urls.py,
test_filter_urls.py,
test_fetch_player_statistics.py
