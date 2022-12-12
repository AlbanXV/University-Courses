from instapy.io import random_image
from instapy.python_filters import python_color2gray, python_color2sepia
import numpy as np

test_array = random_image()
test_shape = (180, 320, 3)

def test_color2gray(image):
    # run color2gray
    gray = python_color2gray(image)
    # check that the result has the right shape, type

    # shape
    assert gray.shape == test_shape
    assert gray.shape[0] == test_shape[0]
    assert gray.shape[1] == test_shape[1]
    assert gray.shape[2] == test_shape[2]

    # type
    assert gray.dtype == test_array.dtype
    np.allclose(gray, test_array)

    # assert uniform r,g,b values
    red = 0.21
    green = 0.72
    blue = 0.07

    x = np.random.randint(gray.shape[0])
    y = np.random.randint(gray.shape[1])

    tot = gray[x][y][0] * red + gray[x][y][1] * green + gray[x][y][2] * blue

    assert int(tot) == int(gray[x][y][0])
    assert int(tot) == int(gray[x][y][1])
    assert int(tot) == int(gray[x][y][2])


def test_color2sepia(image):
    # run color2sepia
    sepia = python_color2sepia(image)
    # check that the result has the right shape, type

    #shape
    assert sepia.shape == test_shape
    assert sepia.shape[0] == test_shape[0]
    assert sepia.shape[1] == test_shape[1]
    assert sepia.shape[2] == test_shape[2]

    # type
    assert sepia.dtype == test_array.dtype
    np.allclose(sepia, test_array)


    # verify some individual pixel samples
    # according to the sepia matrix
    '''
    sepia_matrix = [
        [ 0.393, 0.769, 0.189],
        [ 0.349, 0.686, 0.168],
        [ 0.272, 0.534, 0.131],
    ]
    '''

    x = np.random.randint(sepia.shape[0])
    y = np.random.randint(sepia.shape[1])

    r = sepia[x][y][0]*0.272 + sepia[x][y][1]*0.534 + sepia[x][y][2]*0.131
    g = sepia[x][y][0]*0.349 + sepia[x][y][1]*0.686 + sepia[x][y][2]*0.168
    b = sepia[x][y][0]*0.393 + sepia[x][y][1]*0.769 + sepia[x][y][2]*0.189

    assert int(r) == int(sepia[x][y][0])
    assert int(g) == int(sepia[x][y][1])
    assert int(b) == int(sepia[x][y][2])