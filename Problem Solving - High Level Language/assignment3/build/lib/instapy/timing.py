"""
Timing our filter implementations.

Can be executed as `python3 -m instapy.timing`

For Task 6.
"""
import time
import instapy
from instapy import python_filters
from instapy import numpy_filters
from instapy import numba_filters
from . import io
from typing import Callable
import numpy as np

from PIL import Image

from instapy.python_filters import python_color2gray, python_color2sepia
from instapy.numpy_filters import numpy_color2gray, numpy_color2sepia
from instapy.numba_filters import numba_color2gray, numba_color2sepia


def time_one(filter_function: Callable, *arguments, calls: int = 3) -> float:
    """Return the time for one call

    When measuring, repeat the call `calls` times,
    and return the average.

    Args:
        filter_function (callable):
            The filter function to time
        *arguments:
            Arguments to pass to filter_function
        calls (int):
            The number of times to call the function,
            for measurement
    Returns:
        time (float):
            The average time (in seconds) to run filter_function(*arguments)
    """
    # run the filter function `calls` times
    # return the _average_ time of one call
    ...


def make_reports(filename: str = "test/rain.jpg", calls: int = 3):
    """
    Make timing reports for all implementations and filters,
    run for a given image.

    Args:
        filename (str): the image file to use
    """
    
    # load the image
    image = Image.open(filename)
    #image = io.read_image(filename)
    # print the image name, width, height
    #width, height = image.size
    print(
        f"Timing performed using {filename}: {image.width}x{image.height}"
    )
    # iterate through the filters
    filter_names = ['color2gray', 'color2sepia']
    for filter_name in filter_names:
        # get the reference filter function
        reference_filter = python_filters

        t0 = time.perf_counter()

        if filter_name == "color2gray":
            reference_filter.python_color2gray(image)

        elif filter_name == "color2sepia":
            reference_filter.python_color2sepia(image)
        
        t1 = time.perf_counter

        # time the reference implementation
        reference_time = t1 - t0
        print(
            f"Reference (pure Python) filter time {filter_name}: {reference_time:.3}s ({calls=})"
        )
        # iterate through the implementations
        implementations = ['numpy', 'numba']
        for implementation in implementations:
            #filter = instapy.get_filter(filter_name, implementation)

            ft0 = time.perf_counter()

            if implementation == "numpy" and filter_name == "color2gray":
                numpy_color2gray(image)
            elif implementation == "numpy" and filter_name == "color2sepia":
                numpy_color2sepia(image)
            elif implementation == "numba" and filter_name == "color2gray":
                numba_color2gray(image)
            elif implementation == "numba" and filter_name == "color2sepia":
                numba_color2sepia(image)

            ft1 = time.perf_counter()

            # time the filter
            filter_time = ft1 - ft0
            # compare the reference time to the optimized time
            speedup = reference_time - filter_time
            print(
                f"Timing: {implementation} {filter_name}: {filter_time:.3}s ({speedup=:.2f}x)"
            )


if __name__ == "__main__":
    # run as `python -m instapy.timing`
    make_reports()
