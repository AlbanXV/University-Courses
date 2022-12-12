import re
from typing import Tuple

## -- Task 3 (IN3110 optional, IN4110 required) -- ##

# create array with all names of months
month_names = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
]

...


def get_date_patterns() -> Tuple[str, str, str]:
    """Return strings containing regex pattern for year, month, day
    arguments:
        None
    return:
        year, month, day (tuple): Containing regular expression patterns for each field
    """

    # Regex to capture days, months and years with numbers
    # year should accept a 4-digit number between at least 1000-2029
    year = r"(?P<year>[12][0-9]{3})" # accepts 4 digit from 1000-2999 instead
    # month should accept month names or month numbers
    month = r"(?P<month>(?:(?:January)|(?:February)|(?:March)|(?:April)|(?:May)|(?:June)|(?:July)|(?:August)|(?:September)|(?:October)|(?:November)|(?:December))[a-z]{0,6}|01|02|03|04|05|06|07|08|09|10|11|12)"
    # day should be a number, which may or may not be zero-padded
    day = r"(?P<day>\d\d))"

    return year, month, day


def convert_month(s: str) -> str:
    """Converts a string month to number (e.g. 'September' -> '09'.

    You don't need to use this function,
    but you may find it useful.

    arguments:
        month_name (str) : month name
    returns:
        month_number (str) : month number as zero-padded string
    """
    # If already digit do nothing
    if s.isdigit():
        ...

    # Convert to number as string
    for i, month in enumerate(month_names):
        if month in s:
            if i + 1 < 10:
                return "0" + str(i+1)
            return str(i+1)


def zero_pad(n: str):
    """zero-pad a number string

    turns '2' into '02'

    You don't need to use this function,
    but you may find it useful.
    """
    return "0" + n


def find_dates(text: str, output: str = None) -> list:
    """Finds all dates in a text using reg ex

    arguments:
        text (string): A string containing html text from a website
    return:
        results (list): A list with all the dates found
    """
    year, month, day = get_date_patterns()

    # Date on format YYYY/MM/DD - ISO
    ISO = ...

    # Date on format DD/MM/YYYY
    DMY = ...

    # Date on format MM/DD/YYYY
    MDY = ...

    # Date on format YYYY/MM/DD
    YMD = ...

    # list with all supported formats
    formats = ...
    dates = []

    # find all dates in any format in text
    ...
    # Write to file if wanted
    if output:
        file = open(output, "w")

        for i in dates:
            file.write(f"{i}\n")
        file.close()

    return dates
