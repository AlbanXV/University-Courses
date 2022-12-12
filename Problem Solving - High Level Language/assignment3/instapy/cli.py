"""Command-line (script) interface to instapy"""

import argparse
import sys

import numpy as np
from PIL import Image
from . import python_filters, numba_filters, numpy_filters

import instapy
from . import io

import time

def run_filter(
    file: str,
    out_file: str = None,
    implementation: str = "python",
    filter: str = "color2gray",
    scale: int = 1,
) -> None:
    """Run the selected filter"""
    # load the image from a file
    image = Image.open(file)
    if scale != 1:
        # Resize image, if needed
        image.resize((image.width // 2, image.height // 2))

    # Apply the filter
    if filter == "color2gray":

        #selects the code for the implementation based on chosen "implementatoin" parameter
        selected_filer = {
            "python":python_filters.python_color2gray,
            "numba": numba_filters.numba_color2gray,
            "numpy": numpy_filters.numpy_color2gray,
        }[implementation]
    else:
        selected_filer = {
            "python":python_filters.python_color2sepia,
            "numba": numba_filters.numba_color2sepia,
            "numpy": numpy_filters.numpy_color2sepia
        }[implementation]

    filtered_image = selected_filer(image)
    if out_file:
        grayscale_image = Image.fromarray(filtered_image)
        grayscale_image.save(out_file)
    else:
        # not asked to save, display it instead
        io.display(filtered_image)


def main(argv=None):
    """Parse the command-line and call run_filter with the arguments"""
    if argv is None:
        argv = sys.argv[1:]

    parser = argparse.ArgumentParser()

    # filename is positional and required
    parser.add_argument("file", type=str, help="The filename to apply filter to")
    parser.add_argument("-o", "--out", type=str, help="The output filename")

    # Add required arguments
    parser.add_argument("-g", "--gray", action="store_true", help="Select gray filter")
    parser.add_argument("-se", "--sepia", action="store_true", help="Select sepia filter")
    parser.add_argument("-sc", "--scale", type=int, help="Scale factor to resize image")
    parser.add_argument("-i", "--implementation", choices=['python','numpy','numba'], help="The implementation")

    # Bonus arg (Task 14)
    #parser.add_argument("-r", "--runtime", type=str, action="store_true", help="Track average runtime")

    # parse arguments and call run_filter
    args = parser.parse_args()

    chosen_filter = "color2gray" if args.gray else "color2sepia"

    run_filter(
        file = args.file,
        out_file= args.out,
        implementation = args.implementation,
        filter = chosen_filter,
        scale= args.scale
    )

    '''
    # Bonus - Task 14: Runtime Tracking
    for i in range(3):
        start = time.time()
        # add if check on what filter has been chosen from arg
        stop = time.time()

        runtime = runtime + (stop - start)
    runtime = runtime // 3
    print(
        f"Average time over 3 runs: {runtime}s"
    )
    '''

if __name__ == '__main__':
    main()
