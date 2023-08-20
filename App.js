import React from 'react';
import {Text, NativeModules, TouchableOpacity} from 'react-native';
const {UsageStatsModule} = NativeModules;

// console.log(UsageStatsModule.getUsageStats());

function App() {
  const usageStatsPromise = async () => {
    try {
      var result = await UsageStatsModule.getUsageStats();
      console.log(result);
    } catch (e) {
      console.log(e);
    }
  };
  return (
    <TouchableOpacity onPress={usageStatsPromise} style={{marginLeft:100,marginTop:50,width:180, backgroundColor:'black',padding:20}}>
      <Text style={{fontSize:18}}>Get Usage Stats</Text>
    </TouchableOpacity>
  )
}

export default App;
