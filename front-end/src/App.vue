<script>
// import MyComponent from "./components/MyComponent.vue";

import photoList from "./components/photoList.vue";

export default {
  data() {
    return {
      title: "Photo List",
    };
  },

  components: {
    photoList,
  },
};
</script>

<template>
  <photoList />
</template>

<style lang="scss" scoped></style>
