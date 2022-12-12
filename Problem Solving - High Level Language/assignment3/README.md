# Instapy: Grayscale and Sepia filter

Package adds filters to images. It takes an image and converts it to a 3-dimensional numpy array with RGB channels.
The package applies the filters: Grayscale or Sepia, to the selected image by using three different implementations: pure python, numpy or numba.

## Versions of Dependencies
The requirements for this project is in the `requirements.txt`. Run the following for installing:
```
pip3 install -r requirements.txt
```

## Installation
Package can be installed through pip:
```
python3 -m pip install .
```
or
```
pip install .
```

## How to run the code?

How to run the package:
```
python3 -m instapy <path_to_input_image.jgp> -o <path_to_save_image.png> -i <chosen_implementation> -g
```
or
```
instapy <path_to_input_image.jgp> -o <path_to_save_image.png> -i <chosen_implementation> -g
```

Note that the programe saves the output image in PNG format. Here, the `-g` means grayscal. The user must chosen `-g` or `-se` for filter.


## Testing
You can test the package by using pytest:
```
python -m pytest -v test/test_package.py
```
