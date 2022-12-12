import datetime
from typing import List, Optional

import altair as alt
from fastapi import FastAPI, Query, Request
from fastapi.templating import Jinja2Templates
from starlette.staticfiles import StaticFiles
from strompris import (
    ACTIVITIES,
    LOCATION_CODES,
    fetch_day_prices,
    fetch_prices,
    plot_activity_prices,
    plot_daily_prices,
    plot_prices,
)

from pathlib import Path
base_dir = Path(__file__).resolve().parent

app = FastAPI()
#templates = Jinja2Templates(directory="templates")
templates = Jinja2Templates(directory=Path(base_dir, 'templates'))

# Get static files from Sphinx from directory 'docs'
app.mount(
    "/docs",
    StaticFiles(directory="docs", html=True),
    name="docs"
)


# `GET /` should render the `strompris.html` template
# with inputs:
# - request
# - location_codes: location code dict
# - today: current date

@app.get("/")
def strompris_html(
    request: Request,
    location_codes: dict = LOCATION_CODES,
    today: datetime.date = datetime.date.today()
    ):
    """
    Function to render web application
    
    arguments:
        request: Request
    
    return:
        rendered strompris.html template
    """
    return templates.TemplateResponse(
        "strompris.html",
        {
            "request": request,
            "location_codes": location_codes,
            "today": today,
        },
    )


# GET /plot_prices.json should take inputs:
# - locations (list from Query)
# - end (date)
# - days (int, default=7)
# all inputs should be optional
# return should be a vega-lite JSON chart (alt.Chart.to_dict())
# produced by `plot_prices`
# (task 5.6: return chart stacked with plot_daily_prices)

@app.get("/plot_prices.json")
def plot_prices_json(
    locations: Optional[List[str]] = Query(default=None),
    end: Optional[datetime.date] = None,
    days: Optional[int] = 7
):
    """
    Function to return a json chart from altair

    arguments:
        locations: Optional
        end: Optional
        days: Optional
    
    return:
        json chart

    """
    # If locations is empty and not a tuple:
    if locations is None:
        if locations != tuple:
            locations = LOCATION_CODES.keys()

    df = fetch_prices(end, days, tuple(locations))
    chart = plot_prices(df)
    return chart.to_dict()

# Task 5.6 (Bonus):
# `GET /activity` should render the `activity.html` template
# activity.html template must be adapted from `strompris.html`
# with inputs:
# - request
# - location_codes: location code dict
# - activities: activity energy dict
# - today: current date

...

# Task 5.6 (Bonus):
# `GET /plot_activity.json` should return vega-lite chart JSON (alt.Chart.to_dict())
# from `plot_activity_prices`
# with inputs:
# - location (single, default=NO1)
# - activity (str, default=shower)
# - minutes (int, default=10)

...


# mount your docs directory as static files at `/help`

@app.get("/help")
def help(request: Request):
    return templates.TemplateResponse(
        "help.html",
        {
            "request": request
        }
    )

if __name__ == "__main__":
    # use uvicorn to launch your application on port 5000
    import uvicorn
    uvicorn.run(app, host='localhost', port=5000)
