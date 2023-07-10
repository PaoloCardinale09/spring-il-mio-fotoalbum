<script>
// import MyComponent from "./components/MyComponent.vue";
import photoList from "./photoList.vue";

export default {
  components: {
    photoList,
  },
};
</script>

<template>
  <main>
    <div class="container">
      <photoList />
    </div>
  </main>
</template>

<style lang="scss" scoped></style>
