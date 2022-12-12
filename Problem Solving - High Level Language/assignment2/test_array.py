"""
Tests for our array class
"""

from array_class import Array

# 1D tests (Task 4)


def test_str_1d():

    # int
    arrayTest = Array((5,), 2, 4, 4, 6, 7)
    assert arrayTest.__str__() == "[2, 4, 4, 6, 7]"

    # float
    arrayTest = Array((7,), 1.5, 2.1, 3.2, 4.7, 5.0, 6.1, 7.9)
    assert arrayTest.__str__() == "[1.5, 2.1, 3.2, 4.7, 5.0, 6.1, 7.9]"

    # bool
    arrayTest = Array((5,), True, True, False, False, True)
    assert arrayTest.__str__() == "[True, True, False, False, True]"


def test_add_1d():
    array1 = Array((5,), 2, 4, 4, 6, 7)
    array2 = Array((5,), 1, 1, 1, 1, 1)

    # Array + Array
    assert (array1 + array2) == Array((5,), 3, 5, 5, 7, 8)
    assert (array2 + array1) == Array((5,), 3, 5, 5, 7, 8)

    # int + Array / Array + int
    assert(array1 + 1) == Array((5,), 3, 5, 5, 7, 8)
    assert(1 + array1) == Array((5,), 3, 5, 5, 7, 8)
    assert(1 + array2) == Array((5,), 2, 2, 2, 2, 2)


def test_sub_1d():
    array1 = Array((5,), 2, 4, 4, 6, 7)
    array2 = Array((5,), 1, 1, 1, 1, 1)

    assert (array1 - array2) == Array((5,), 1, 3, 3, 5, 6)
    assert (array2 - array1) == Array((5,), -1, -3, -3, -5, -6)

    assert(array1 - 1) == Array((5,), 1, 3, 3, 5, 6)
    assert(1 - array1) == Array((5,), 1-2, 1-4, 1-4, 1-6, 1-7)
    assert(1 - array2) == Array((5,), 0, 0, 0, 0, 0)

def test_mul_1d():
    array1 = Array((5,), 2, 4, 4, 6, 7)
    array2 = Array((5,), 3, 3, 3, 3, 3)

    assert (array1 * array2) == Array((5,), 2*3, 4*3, 4*3, 6*3, 7*3)
    assert (array2 * array1) == Array((5,), 3*2, 3*4, 3*4, 3*6, 3*7)

    assert(array1 * 5) == Array((5,), 2*5, 4*5, 4*5, 6*5, 7*5)
    assert(5 * array1) == Array((5,), 5*2, 5*4, 5*4, 5*6, 5*7)
    assert(10 * array2) == Array((5,), 10*3, 10*3, 10*3, 10*3, 10*3)


def test_eq_1d():
    array1 = Array((5,), 2, 4, 4, 6, 7)
    array2 = Array((5,), 3, 3, 3, 3, 3)
    array3 = Array((5,), 2, 4, 4, 6, 7)

    assert (array1 == array3) == True
    assert (array1 == array2) == False
    assert (array2 == array3) == False


def test_same_1d():
    array1 = Array((5,), 2, 4, 4, 6, 7)
    array2 = Array((5,), 3, 4, 4, 1, 3)

    boolArray = array1.is_equal(array2)

    assert boolArray == Array((5,), False, True, True, False, False)

def test_smallest_1d():
    array1 = Array((5,), 2, 4, 4, 6, 7)
    array2 = Array((5,), 3, 4, 4, 1, 3)
    array3 = Array((5,), 0, 1, 2, 3, 4)

    assert array1.min_element() == 2
    assert array2.min_element() == 1
    assert array3.min_element() == 0

def test_mean_1d():
    array1 = Array((4,), 1, 2, 3, 4)
    array2 = Array((5,), 2, 4, 6, 8, 10)
    array3 = Array((5,), 1, 3, 2, 0, 5)

    assert array1.mean_element() == 2.5
    assert array2.mean_element() == 6.0
    assert array3.mean_element() == 2.2


# 2D tests (Task 6)


def test_add_2d():
    array1 = Array((3, 2), 8, 3, 4, 1, 6, 1)
    array2 = Array((3, 2), 1, 2, 3, 4, 5, 6)

    # Arrays
    assert (array1 + array2) == Array((3, 2), 9, 5, 7, 5, 11, 7)
    assert (array2 + array1) == Array((3, 2), 9, 5, 7, 5, 11, 7)

    # int
    assert (array1 + 1) == Array((3, 2), 9, 4, 5, 2, 7, 2)
    assert (1 + array1) == Array((3, 2), 9, 4, 5, 2, 7, 2)


def test_mult_2d():
    array1 = Array((3, 2), 2, 2, 2, 2, 2, 2)
    array2 = Array((3, 2), 1, 1, 1, 1, 1, 1)

    # Arrays
    assert (array1 * array2) == Array((3, 2), 2, 2, 2, 2, 2, 2)
    assert (array2 * array1) == Array((3, 2), 2, 2, 2, 2, 2, 2)

    # int
    assert (array1 * 1) == Array((3, 2), 2, 2, 2, 2, 2, 2)
    assert (1 * array1) == Array((3, 2), 2, 2, 2, 2, 2, 2)


def test_same_2d():
    array1 = Array((3, 2), 8, 3, 4, 1, 6, 1)
    array2 = Array((3, 2), 1, 2, 4, 4, 6, 6)

    boolArray = array1.is_equal(array2)

    assert boolArray == Array((3, 2), False, False, True, False, True, False)

def test_mean_2d():
    array1 = Array((2, 2), 1, 2, 3, 4)
    array2 = Array((3, 2), 2, 4, 6, 1, 2, 3)

    assert array1.mean_element() == 2.5
    assert array2.mean_element() == 3.0


if __name__ == "__main__":
    """
    Note: Write "pytest" in terminal in the same folder as this file is in to run all tests
    (or run them manually by running this file).
    Make sure to have pytest installed (pip install pytest, or install anaconda).
    """

    # Task 4: 1d tests
    test_str_1d()
    test_add_1d()
    test_sub_1d()
    test_mul_1d()
    test_eq_1d()
    test_mean_1d()
    test_same_1d()
    test_smallest_1d()

    # Task 6: 2d tests
    test_add_2d()
    test_mult_2d()
    test_same_2d()
    test_mean_2d()
