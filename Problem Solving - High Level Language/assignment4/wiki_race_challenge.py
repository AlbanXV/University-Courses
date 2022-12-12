from typing import List  # isort:skip
from collections import deque
from bs4 import BeautifulSoup
import requesting_urls as req
import time

def find_path(start: str, finish: str) -> List[str]:
    """Find the shortest path from `start` to `finish`

    Arguments:
      start (str): wikipedia article URL to start from
      finish (str): wikipedia article URL to stop at

    Returns:
      urls (list[str]):
        List of URLs representing the path from `start` to `finish`.
        The first item should be `start`.
        The last item should be `finish`.
        All items of the list should be URLs for wikipedia articles.
        Each article should have a direct link to the next article in the list.
    """
    path = {}
    path[start] = [start]

    queue = deque()
    queue.append(start)

    while queue:
      node = queue.popleft()
      urls = get_url(node)

      for i in urls:

        if i == finish:
          return path[node] + [i]
        
        if i != path and i != node:
          path[i] = path[node] + [i]
          queue.append(i)


    assert path[0] == start
    assert path[-1] == finish
    return path

def get_url(url):
  html = req.get_html(url)
  soup = BeautifulSoup(html, "html.parser")

  base = url[:url.find('/wiki/')]
  urls = list({base + a['href'] for a in soup.select('p a[href]') if a['href'].startswith('/wiki/')})

  return urls

def timer(x):
  present = time.time()
  print(f"\n Total time used: {present - x} secs")

if __name__ == "__main__":
    start = "https://en.wikipedia.org/wiki/Python_(programming_language)"
    finish = "https://en.wikipedia.org/wiki/Peace"

    start_time = time.time()
    print(find_path(start, finish))
    timer(start_time)

    # Total time used: 418 seconds on my pc when I tested it.
