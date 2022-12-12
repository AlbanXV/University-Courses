from instapy.io import random_image
from instapy.numpy_filters import numpy_color2gray, numpy_color2sepia
from instapy.python_filters import python_color2gray, python_color2sepia

import numpy as np
import numpy.testing as nt

test_array = random_image()
test_shape = (180, 320, 3)


def test_color2gray(image, reference_gray):
    gray = python_color2gray(image)
    reference_gray = numpy_color2gray(image)

    #nt.assert_allclose(gray, reference_gray)

    # shape
    assert reference_gray.shape == test_shape
    assert reference_gray.shape[0] == test_shape[0]
    assert reference_gray.shape[1] == test_shape[1]
    assert reference_gray.shape[2] == test_shape[2]

    #type
    assert reference_gray.dtype == gray.dtype
    assert reference_gray.dtype == test_array.dtype
    np.allclose(reference_gray, test_array)
    np.allclose(reference_gray, gray)

    # assert uniform r,g,b values
    red = 0.21
    green = 0.72
    blue = 0.07

    x = np.random.randint(reference_gray.shape[0])
    y = np.random.randint(reference_gray.shape[1])

    tot = reference_gray[x][y][0] * red + reference_gray[x][y][1] * green + reference_gray[x][y][2] * blue

    assert int(tot) == int(reference_gray[x][y][0])
    assert int(tot) == int(reference_gray[x][y][1])
    assert int(tot) == int(reference_gray[x][y][2])


def test_color2sepia(image, reference_sepia):
    sepia = python_color2sepia(image)
    reference_sepia = numpy_color2sepia(image)

    #nt.assert_allclose(sepia, reference_sepia)

    # shape
    assert reference_sepia.shape == test_shape
    assert reference_sepia.shape[0] == test_shape[0]
    assert reference_sepia.shape[1] == test_shape[1]
    assert reference_sepia.shape[2] == test_shape[2]

    #type
    assert reference_sepia.dtype == sepia.dtype
    assert reference_sepia.dtype == test_array.dtype
    np.allclose(reference_sepia, test_array)
    np.allclose(reference_sepia, sepia)

    # verify some individual pixel samples
    # according to the sepia matrix
    '''
    sepia_matrix = [
        [ 0.393, 0.769, 0.189],
        [ 0.349, 0.686, 0.168],
        [ 0.272, 0.534, 0.131],
    ]
    '''

    x = np.random.randint(reference_sepia.shape[0])
    y = np.random.randint(reference_sepia.shape[1])

    r = reference_sepia[x][y][0]*0.272 + reference_sepia[x][y][1]*0.534 + reference_sepia[x][y][2]*0.131
    g = reference_sepia[x][y][0]*0.349 + reference_sepia[x][y][1]*0.686 + reference_sepia[x][y][2]*0.168
    b = reference_sepia[x][y][0]*0.393 + reference_sepia[x][y][1]*0.769 + reference_sepia[x][y][2]*0.189

    assert int(r) == int(reference_sepia[x][y][0])
    assert int(g) == int(reference_sepia[x][y][1])
    assert int(b) == int(reference_sepia[x][y][2])
