package com.example.downloadmanager

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.downloadmanager.ui.theme.PurpleGrey40
@Composable
fun CustomProgressbar(
     progressCount : Int ,
     onValueChanged : (Int)->Unit ={},
     modifier:Modifier  = Modifier
){
     var progress by remember { mutableStateOf(0.0f) }
     val context = LocalContext.current
    val size by animateFloatAsState(
         targetValue = progress,
         tween(
              durationMillis = 1000,
               delayMillis = 200,
              easing = LinearOutSlowInEasing
         )
    )


     LaunchedEffect(key1 = progressCount){


          if(progressCount == 0){
               progress = 0.0f
          }else if(progressCount in 1..9){
               progress = 0.05f
          }else if(progressCount==10){
               progress = 0.1f
          }else if(progressCount in 11..19){
               progress = 0.15f
          }else if(progressCount==20){
               progress = 0.2f
          }else if(progressCount in 21..29) {
               progress = 0.25f
          }else if(progressCount==30){
               progress = 0.3f
          }else if(progressCount in 31..39) {
               progress = 0.35f
          } else if(progressCount==40){
               progress = 0.4f
          } else if(progressCount in 41..49){
               progress = 0.45f
          }else if(progressCount==50){
               progress = 0.5f
          }else if(progressCount in 51..59){
          progress = 0.55f
          } else if(progressCount==60){
               progress = 0.6f
          }else if(progressCount in 61..69){
          progress = 0.65f
          }else if(progressCount==70){
               progress = 0.7f
          }else if(progressCount in 71..79){
          progress = 0.75f
          }else if(progressCount==80){
               progress = 0.8f
          }else if(progressCount in 81..89){
               progress = 0.85f
          }else if(progressCount==90){
               progress = 0.9f
          }else if(progressCount in 91..99){
               progress = 0.95f
          }else if(progressCount==100){
               progress = 1f
          }
}




     Column(
          modifier = Modifier
               .wrapContentSize()
               .padding(top = 10.dp, start = 30.dp, end = 30.dp),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
     ){
          Row(modifier= Modifier
               .widthIn(min = 30.dp)
               .fillMaxWidth(),
               horizontalArrangement = Arrangement.End
          ){
               Log.d("suraj","$progressCount progresscount")
               Text(text = "$progressCount%")
          }
          //progressbar
          Box(
               modifier = Modifier
                    .fillMaxWidth()
                    .height(17.dp)
          ) {
               //for the background of the progressbar
               Box(
                    modifier = Modifier
                         .fillMaxSize()
                         .clip(RoundedCornerShape(9.dp))
                         .background(Color.White)
               )
               //for the progress of the progressbar
               Box(
                    modifier= Modifier
                         .fillMaxWidth(progress)
                         .fillMaxHeight()
                         .clip(RoundedCornerShape(9.dp))
                         .background(Color.Blue)
                         .animateContentSize()
               )
          }
     }
}