"""numba-optimized filters"""
from numba import jit
import numpy as np


@jit(nopython=True)
def numba_color2gray(image: np.array) -> np.array:
    """Convert rgb pixel array to grayscale

    Args:
        image (np.array)
    Returns:
        np.array: gray_image
    """
    gray_image = np.empty_like(image)

    red = 0.21
    green = 0.72
    blue = 0.07
    
    # iterate through the pixels, and apply the grayscale transform
    for i in range(len(gray_image)):
        for j in range(len(gray_image[i])):
            
            gray = gray_image[i][j][0] * red + gray_image[i][j][1] * green + gray_image[i][j][2] * blue

            gray_image[i][j] = (gray, gray, gray)
    
    gray_image = gray_image.astype("uint8")
    return gray_image


@jit(nopython=True)
def numba_color2sepia(image: np.array) -> np.array:
    """Convert rgb pixel array to sepia

    Args:
        image (np.array)
    Returns:
        np.array: sepia_image
    """
    sepia_image = np.empty_like(image)
    # Iterate through the pixels
    # applying the sepia matrix

    sepia_matrix = [
        [ 0.393, 0.769, 0.189],
        [ 0.349, 0.686, 0.168],
        [ 0.272, 0.534, 0.131],
    ]

    for i in range(len(sepia_image)):
        for j in range(len(sepia_image[i])):

            r = sepia_image[i][j][0]
            g = sepia_image[i][j][1]
            b = sepia_image[i][j][2]

            red = r * sepia_matrix[0][0] + g * sepia_matrix[0][1] + b * sepia_matrix[0][2]
            green = r * sepia_matrix[1][0] + g * sepia_matrix[1][1] + b * sepia_matrix[1][2]
            blue = r * sepia_matrix[2][0] + g * sepia_matrix[2][1] + b * sepia_matrix[2][2]

            sepia_image[i][j] = (min(255, red), min(255, green), min(255, blue))
    
    sepia_image = sepia_image.astype("uint8")

    # Return image
    # don't forget to make sure it's the right type!
    return sepia_image
