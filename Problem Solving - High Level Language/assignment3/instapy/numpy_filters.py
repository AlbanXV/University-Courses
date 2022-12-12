"""numpy implementation of image filters"""

from typing import Optional
import numpy as np
#from PIL import Image

def numpy_color2gray(image: np.array) -> np.array:
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

    # Hint: use numpy slicing in order to have fast vectorized code
    r = gray_image[:,:,0:1]
    g = gray_image[:,:,1:2]
    b = gray_image[:,:,2:3]

    gray_image[:,:,:] = r * red + g * green + b * blue

    gray_image = gray_image.astype("uint8")
    #img = Image.fromarray(gray_image, "RGB")
    #img.show()

    # Return image (make sure it's the right type!)
    return gray_image

def numpy_color2sepia(image: np.array, k: Optional[float] = 1) -> np.array:
    """Convert rgb pixel array to sepia

    Args:
        image (np.array)
        k (float): amount of sepia filter to apply (optional)

    The amount of sepia is given as a fraction, k=0 yields no sepia while
    k=1 yields full sepia.

    (note: implementing 'k' is a bonus task,
    you may ignore it for Task 9)

    Returns:
        np.array: sepia_image
    """

    if not 0 <= k <= 1:
        # validate k (optional)
        raise ValueError(f"k must be between [0-1], got {k}")

    sepia_image = np.empty_like(image)

    # define sepia matrix (optional: with `k` tuning parameter for bonus task 13)
    sepia_matrix = [
        [ 0.393, 0.769, 0.189],
        [ 0.349, 0.686, 0.168],
        [ 0.272, 0.534, 0.131],
    ]

    # HINT: For version without adaptive sepia filter, use the same matrix as in the pure python implementation
    # use Einstein sum to apply pixel transform matrix
    # Apply the matrix filter
    sepia_image = np.einsum('hwk, ck->hwc', sepia_image, sepia_matrix)
    #sepia_image = np.matmul(sepia_matrix, sepia_image[..., np.newaxis])
    sepia_image = np.squeeze(sepia_image)


    # Check which entries have a value greater than 255 and set it to 255 since we can not display values bigger than 255
    np.clip(sepia_image, None, 255, out=sepia_image) #using np.clip to set max value to 255

    # Return image (make sure it's the right type!)
    sepia_image = sepia_image.astype("uint8")

    #img = Image.fromarray(sepia_image, "RGB")
    #img.show()

    return sepia_image

#img = Image.open("C:/Users/Alban/Documents/IN3110/albanba/assignment3/test/rain.jpg")
#numpy_color2gray(img)
#numpy_color2sepia(img)