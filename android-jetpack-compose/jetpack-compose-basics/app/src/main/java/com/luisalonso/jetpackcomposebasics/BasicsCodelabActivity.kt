package com.luisalonso.jetpackcomposebasics

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luisalonso.jetpackcomposebasics.ui.theme.BasicCodelabTheme
import com.luisalonso.jetpackcomposebasics.ui.theme.JetpackcomposebasicsTheme

val defaultNames = List(10000) { "Hello Android $it" }

class BasicsCodelabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyScreenContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    BasicCodelabTheme {
        Surface(color = Color.Blue) {
            content()
        }
    }
}

@Composable
fun Greeting(name: String) {
    val isSelected = remember(calculation = {
        mutableStateOf(false)
    })
    val backgroundColor =
        animateColorAsState(targetValue = if (isSelected.value) Color.Cyan else Color.Transparent)

    Text(
        text = "Hello $name!",
        modifier = Modifier
            .padding(18.dp)
            .background(color = backgroundColor.value)
            .clickable(onClick = {
                isSelected.value = isSelected.value.not()
            }),
        style = MaterialTheme.typography.h6
    )
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { updateCount(count + 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 10) Color.Red else if (count > 5) Color.Green else Color.Yellow
        )
    ) {
        Text(text = "I've been clicked $count ${if (count == 1) "time" else "times"}")
    }
}

@Composable
fun NameList(names: List<String>, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) { name ->
            Greeting(name = name)
            Divider(color = Color.Cyan)
        }
    }
}

@Composable
fun MyScreenContent(names: List<String> = defaultNames) {
    val counterState = remember(calculation = {
        mutableStateOf(0)
    })

    Column(modifier = Modifier.fillMaxHeight()) {
//        Column(Modifier.weight(1f)) {
//            names.forEach {
//                Greeting(name = it)
//                Divider(color = Color.Cyan)
//            }
//        }
        NameList(names = names, modifier = Modifier.weight(1f))
        // Spacer(modifier = Modifier.height(24.dp))
        Counter(count = counterState.value) { newCount ->
            counterState.value = newCount
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BasicCodelabDefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}

@Preview(showBackground = true)
@Composable
fun ThemingMyAppPreview() {
    BasicCodelabTheme {
        Greeting("Android")
    }
}