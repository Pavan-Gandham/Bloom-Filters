## Bloom-Filters
A Bloom filter is a space-efficient probabilistic data structure that is used to test whether an element is a member of a set. For example, checking availability of
username is a set membership problem, where the set is the list of all registered usernames. The price we pay for efficiency is that it is probabilistic in nature that
means, there might be some False Positive results. False positive means, it might tell that a given username is already taken but actually it’s not.

# Bloom Filter Visulization using Swing
The visualisation is done by SWING which is a GUI widget toolkit for Java.The below picture is the snapshot of the GUI.
![image](https://user-images.githubusercontent.com/97956699/187749109-c1ea5ad7-438c-4563-9b81-97e460997a6c.png)

# Bloom Filter Implementation
The algorithm is based on a bit vector of size 6400 and 4 independent and uniformly distributed hash functions. When a new element is handled by the filter, it's hashed
against each of the functions.Their results correspond to the bit vector indexes that will be set to 1
![image](https://user-images.githubusercontent.com/97956699/187748886-0a2cd1be-7493-4304-ae3f-a3142a004bfb.png)

# The query response of Bloom Filter is very fast, and it is in O(1) time complexity.
# The space complexity of the Bloom Filter having n elements has O(n).
