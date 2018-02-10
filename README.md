# MaterialChips

An implementation of Chips as specified by [Google’s Material Design](https://material.io/guidelines/components/chips.html).

## Features

### OnClickListener

    chips_view.onClickListener = object : ChipsView.OnClickListener() {
        override fun onDelete(item: Any, position: Int) {
            Toast.makeText(context, alertItemClicked, Toast.LENGTH_SHORT).show()
        }
    }

### OnDeleteListener

    chips_view.onDeleteListener = object : ChipsView.OnDeleteListener() {
        override fun onDelete(item: Any, position: Int): Boolean {
            Toast.makeText(context, alertItemDeleted, Toast.LENGTH_SHORT).show()
            return true
        }
    }
