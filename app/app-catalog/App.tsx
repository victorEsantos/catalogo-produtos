import React from "react";
import { View, Text ,StyleSheet} from "react-native";

const App = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.h1}>Alo</Text>
    </View>
  );
};

const styles = StyleSheet.create({
    container: {
      backgroundColor: "#069",
      flex: 1,
      flexDirection: "column",
      alignItems: "center",
      justifyContent: "center",
    },
    h1: {
        fontSize:24,
        fontWeight:'700'
    }
  });

export default App;
