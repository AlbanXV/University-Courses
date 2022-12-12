"""
Array class for assignment 2
"""

class Array:

    def __init__(self, shape, *values):
        """Initialize an array of 1-dimensionality. Elements can only be of type:

        - int
        - float
        - bool

        Make sure the values and shape are of the correct type.

        Make sure that you check that your array actually is an array, which means it is homogeneous (one data type).

        Args:
            shape (tuple): shape of the array as a tuple. A 1D array with n elements will have shape = (n,).
            *values: The values in the array. These should all be the same data type. Either int, float or boolean.

        Raises:
            TypeError: If "shape" or "values" are of the wrong type.
            ValueError: If the values are not all of the same type.
            ValueError: If the number of values does not fit with the shape.
        """

        # Bool to check if array is 2D or 1D:
        self._2d = False

        # Rows and columns:
        self._row = None
        self._col = None

        # Check if Array is 2D:
        if len(shape) == 2:
            self._2d = True
            self._row = shape[0]
            self._col = shape[1]

        # Check if the values are of valid types
        # Check that the amount of values corresponds to the shape

        if (type(values[0]) != int and type(values[0]) != float and type(values[0]) != bool):
            print("Shape is wrong type.")
            raise TypeError
        
        if (type(shape) != tuple):
            print("Value is wrong type.")
            raise TypeError

        # 1D
        if self._2d == False:
            if len(values) != shape[0]:
                print("Number of values does not correspond to the shape.")
                raise ValueError
        # 2D
        else:
            if len(values) / self._row != self._col:
                raise ValueError
        
        for i in values:
            if type(i) != type(values[0]):
                print("Values are not all of the same type.")
                raise ValueError

        # Set class-variables
        self._values = values
        self._shape = shape
        self._type = type(values[0])

    def twoDimensional(self):
        """
        Returns a 2D array by creating two lists that go through
        rows and columns and appending / storing the values in a nested list.

        Args: None
        
        Returns:
            nested list: 2D array where values are stored from self._values in a list
        """
        
        temp = list()

        counter = 0

        for i in range(self._row):
            tempK = list()
            for j in range(self._col):
                tempK.append(self._values[i])
                counter += 1
            temp.append(tempK)
        return temp


    def __getitem__(self, index):
        """
        Returns element of index in array.

        Args: index (int)

        Returns:
            int, float, boolean
        """

        # 1D
        if self._2d == False:
            return self._values[index]  

        # 2D
        else:
            row = index
            col = index

            twoD = self.twoDimensional()

            return twoD[row][col]

    def __str__(self):
        """Returns a nicely printable string representation of the array.

        Returns:
            str: A string representation of the array.

        """
        arr = str(self._values)

        arr = arr.replace("(", "[")
        arr = arr.replace(")", "]")

        return arr

    def __add__(self, other):
        """Element-wise adds Array with another Array or number.

        If the method does not support the operation with the supplied arguments
        (specific data type or shape), it should return NotImplemented.

        Args:
            other (Array, float, int): The array or number to add element-wise to this array.

        Returns:
            Array: the sum as a new array.

        """

        # check that the method supports the given arguments (check for data type and shape of array)
        # if the array is a boolean you should return NotImplemented

        if (type(other) != Array and type(other) != int and type(other) != float):
            return NotImplemented
        
        if isinstance(other, (int, float)):
            
            temp = list()

            # Adderer:
            for i in self._values:
                temp.append(i + other)
            return Array(self._shape, *temp)

        elif self._shape[0] != other.hent_shape()[0]:
            return NotImplemented
        
        temp = list()

        # Adderer:
        for i in range(len(self._values)):
            temp.append(self._values[i] + other.hent_values()[i])
        return Array(self._shape, *temp)

    def __radd__(self, other):
        """Element-wise adds Array with another Array or number.

        If the method does not support the operation with the supplied arguments
        (specific data type or shape), it should return NotImplemented.

        Args:
            other (Array, float, int): The array or number to add element-wise to this array.

        Returns:
            Array: the sum as a new array.

        """
        return self.__add__(other)

    def __sub__(self, other):
        """Element-wise subtracts an Array or number from this Array.

        If the method does not support the operation with the supplied arguments
        (specific data type or shape), it should return NotImplemented.

        Args:
            other (Array, float, int): The array or number to subtract element-wise from this array.

        Returns:
            Array: the difference as a new array.

        """
        if (type(other) != Array and type(other) != int and type(other) != float):
            return NotImplemented
        
        if isinstance(other, (int, float)):
            
            temp = list()

            # Subtraherer:
            for i in self._values:
                temp.append(i - other)
            return Array(self._shape, *temp)

        elif self._shape[0] != other.hent_shape()[0]:
            return NotImplemented
        
        temp = list()

        # Subtraherer:
        for i in range(len(self._values)):
            temp.append(self._values[i] - other[i])
        return Array(self._shape, *temp)

    def __rsub__(self, other):
        """Element-wise subtracts this Array from a number or Array.

        If the method does not support the operation with the supplied arguments
        (specific data type or shape), it should return NotImplemented.

        Args:
            other (Array, float, int): The array or number being subtracted from.

        Returns:
            Array: the difference as a new array.

        """
        if (type(other) != Array and type(other) != int and type(other) != float):
            return NotImplemented
        
        if isinstance(other, (int, float)):
            
            temp = list()

            # Subtraherer (Reverse):
            for i in self._values:
                temp.append(other - i)
            return Array(self._shape, *temp)

        elif self._shape[0] != other.hent_shape()[0]:
            return NotImplemented
        
        temp = list()

        # Subtraherer (Reverse):
        for i in range(len(self._values)):
            temp.append(other[i] - self._values[i])
        return Array(self._shape, *temp)

    def __mul__(self, other):
        """Element-wise multiplies this Array with a number or array.

        If the method does not support the operation with the supplied arguments
        (specific data type or shape), it should return NotImplemented.

        Args:
            other (Array, float, int): The array or number to multiply element-wise to this array.

        Returns:
            Array: a new array with every element multiplied with `other`.

        """
        if (type(other) != Array and type(other) != int and type(other) != float):
            return NotImplemented
        
        if isinstance(other, (int, float)):
            
            temp = list()

            # Multipliserer:
            for i in self._values:
                temp.append(i * other)
            return Array(self._shape, *temp)

        elif self._shape[0] != other.hent_shape()[0]:
            return NotImplemented
        
        temp = list()

        # Multipliserer:
        for i in range(len(self._values)):
            temp.append(self._values[i] * other.hent_values()[i])
        return Array(self._shape, *temp)

    def __rmul__(self, other):
        """Element-wise multiplies this Array with a number or array.

        If the method does not support the operation with the supplied arguments
        (specific data type or shape), it should return NotImplemented.

        Args:
            other (Array, float, int): The array or number to multiply element-wise to this array.

        Returns:
            Array: a new array with every element multiplied with `other`.

        """
        # Hint: this solution/logic applies for all r-methods
        return self.__mul__(other)

    def __eq__(self, other):
        """Compares an Array with another Array.

        If the two array shapes do not match, it should return False.
        If `other` is an unexpected type, return False.

        Args:
            other (Array): The array to compare with this array.

        Returns:
            bool: True if the two arrays are equal (identical). False otherwise.

        """
        if type(other) != Array:
            return False

        if self._2d:
            if self._shape[0] != other.hent_shape()[0]:
                raise ValueError
            if self._shape[1] != other.hent_shape()[1]:
                raise ValueError
        else:
            if len(other.hent_shape()) > 1:
                raise ValueError
            elif self._shape[0] != other.hent_shape()[0]:
                raise ValueError
        
        for i in range(len(other.hent_values())):
            if self._values[i] != other.hent_values()[i]:
                return False
        return True

    def is_equal(self, other):
        """Compares an Array element-wise with another Array or number.

        If `other` is an array and the two array shapes do not match, this method should raise ValueError.
        If `other` is not an array or a number, it should return TypeError.

        Args:
            other (Array, float, int): The array or number to compare with this array.

        Returns:
            Array: An array of booleans with True where the two arrays match and False where they do not.
                   Or if `other` is a number, it returns True where the array is equal to the number and False
                   where it is not.

        Raises:
            ValueError: if the shape of self and other are not equal.

        """
        # Hvis Array og int:
        if type(other) != Array and type(other) != int and type(other) != float:
            raise TypeError
        
        # Hvis other sin type er array men matcher ikke m/ hverandre:
        if isinstance(other, Array):
            if self._2d:
                if self._shape[0] != other.hent_shape()[0]:
                    raise ValueError
                if self._shape[1] != other.hent_shape()[1]:
                    raise ValueError
            else:
                if len(other.hent_shape()) > 1:
                    raise ValueError
                elif self._shape[0] != other.hent_shape()[0]:
                    raise ValueError
        
        temp = list()

        # Hvis int eller float:
        if isinstance(other, (int, float)):
            # Gaa gjennom verdier:
            for i in self._values:
                #Hvis likt, legg til i lista: True, ellers False:
                if i == other:
                    temp.append(True)
                else:
                    temp.append(False)
        else:
            # Gaa gjennom lengden av other's verdier:
            for i in range(len(other.hent_values())):
                #Hvis likt, legg til i lista: True, ellers False:
                if self._values[i] == other.hent_values()[i]:
                    temp.append(True)
                else:
                    temp.append(False)
        return Array(self._shape, *temp)

    def min_element(self):
        """Returns the smallest value of the array.

        Only needs to work for type int and float (not boolean).

        Returns:
            float: The value of the smallest element in the array.

        """

        if self._type != bool:
            
            min = self._values[0]

            for i in self._values:
                if i <= min:
                    min = i
            return float(min)
        print("Only type int and float allowed.")
        raise TypeError

    def mean_element(self):
        """Returns the mean value of an array

        Only needs to work for type int and float (not boolean).

        Returns:
            float: the mean value
        """

        if self._type != bool:
            counter = 0
            for i in self._values:
                counter += i
                mean = float(counter / len(self._values))
            return mean
        print("Only type int and float allowed.")
        raise TypeError

    def hent_shape(self):
        return self._shape
    
    def hent_values(self):
        return self._values

    def hent_type(self):
        return self._type