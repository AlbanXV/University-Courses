import datetime
import altair as alt
import pandas as pd
import requests

def fetch_day_prices(date: datetime.date = None, location: str = "NO1") -> pd.DataFrame:
    if date is None:
        date = datetime.date.today()
    url = "https://www.hvakosterstrommen.no/api/v1/prices/"


    # Check to see if date is after 2nd October 2022
    # If not raise AssertionError and ValueError
    #invalid_date = pd.to_datetime("2022-10-02").date()
    invalid_date = datetime.date(2022,10,2)
    #assert (date > invalid_date)
    if not (date > invalid_date):
        #print("Date is before 2nd October 2022")
        raise ValueError("Date is before 2nd October 2022")

    # Extract the date and location from arguments and add it to the url
    # Fetch the data and add return it as dataframe containing specific
    # columns
    date_now = date.strftime("%Y/%m-%d")
    date_url = f"{url}{date_now}_{location}.json"
    r = requests.get(date_url).text
    data = pd.DataFrame(eval(r))
    df = data[['NOK_per_kWh', 'time_start']]

    return df
    

#print(fetch_day_prices())
#print(fetch_day_prices(datetime.date(2022, 10, 2), "NO1")) # Error
#print(fetch_day_prices(datetime.date(2022, 11, 10), "NO5"))

#date = datetime.date.today()
#print(f"full: {date}, dag: {date.day}, måned: {date.month}, år: {date.year}")

#eks = date.strftime("%Y/%m-%d")
#print(eks)

LOCATION_CODES = {
    "NO1":"Oslo / Øst-Norge",
    "NO2":"Kristiansand / Sør-Norge",
    "NO3":"Trondheim / Midt-Norge",
    "NO4":"Tromsø / Nord-Norge",
    "NO5":"Bergen / Vest-Norge",
}

def fetch_prices(
    end_date: datetime.date = None,
    days: int = 7,
    locations=tuple(LOCATION_CODES.keys()),
) -> pd.DataFrame:
    """Fetch prices for multiple days and locations into a single DataFrame

    arguments:
        ...
    
    return:
        ...
    """
    if end_date is None:
        end_date = datetime.date.today()

    df = pd.DataFrame()

    for location in locations:
        for day in range(days):
            temp = fetch_day_prices(end_date - datetime.timedelta(day), location)
            temp["location_code"] = location
            temp["location"] = LOCATION_CODES[location]
            df = pd.concat([df, temp])
    
    return df

print(fetch_prices())
date = datetime.date.today()
        
'''
for i in range(days):
    #print(i)
    future = date - datetime.timedelta(i)
    print(future)
'''

#future = date + datetime.timedelta(days)
#print(future)

day = date.day
month = date.month
year = date.year

url = "https://www.hvakosterstrommen.no/api/v1/prices/2022/11-06_NO3.json"
r = requests.get(url).text
data = r
#print(type(data))

#df = pd.DataFrame(eval(data))
#print(df)

#df1 = df[['NOK_per_kWh', 'time_start']]
#print(df1)

#print(date)
#print(day)
#print(month)
#print(year)

#assert (str(date) > "2022-10-17")
#assert ("2022-10-16" > "2022-10-17")
#if not ("2022-10-16" > "2022-10-17"):
#    print("stinky")
